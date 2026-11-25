package com.merida.tecnm.market_backend_v2.web.controller;

import com.merida.tecnm.market_backend_v2.domain.Purchase;
import com.merida.tecnm.market_backend_v2.domain.PurchaseItem;
import com.merida.tecnm.market_backend_v2.domain.service.PurchaseService;
import com.merida.tecnm.market_backend_v2.web.dto.PurchaseRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase", description = "Manage purchases (Master-Detail)")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    @Operation(summary = "Get all purchases", description = "Return a list of all registered purchases")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of purchases")
    @ApiResponse(responseCode = "500", description = "Internal Server error")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    @Operation(summary = "Get purchases by client ID", description = "Return all purchases made by a specific client")
    @ApiResponse(responseCode = "200", description = "Purchases found")
    @ApiResponse(responseCode = "404", description = "Purchases not found for client")
    @ApiResponse(responseCode = "500", description = "Internal Server error")
    public ResponseEntity<List<Purchase>> getByClient(
            @Parameter(description = "Client ID", example = "CLI-001", required = true)
            @PathVariable("id") String clientId) {
        return purchaseService.getByClient(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(
            summary = "Create a new purchase (Master-Detail)",
            description = "Register a purchase header and its purchased products in a single transaction",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Purchase",
                                    value = """
                                            {
                                              "idCliente": "CLI-001",
                                              "medioPago": "TARJETA",
                                              "comentario": "Entregar por la tarde",
                                              "productosAdquiridos": [
                                                {
                                                  "idProducto": 15,
                                                  "cantidad": 2,
                                                  "total": 500.0
                                                },
                                                {
                                                  "idProducto": 8,
                                                  "cantidad": 1,
                                                  "total": 150.0
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Purchase created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid purchase data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Purchase> save(@RequestBody PurchaseRequestDTO request) {
        Purchase purchase = toPurchase(request);
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }

    private Purchase toPurchase(PurchaseRequestDTO request) {
        Purchase purchase = new Purchase();
        purchase.setClientId(request.getIdCliente());
        purchase.setPaymentMethod(request.getMedioPago());
        purchase.setComment(request.getComentario());
        purchase.setDate(LocalDateTime.now());
        purchase.setState("P");

        List<PurchaseItem> items = request.getProductosAdquiridos() == null
                ? Collections.emptyList()
                : request.getProductosAdquiridos().stream().map(dto -> {
                    PurchaseItem item = new PurchaseItem();
                    item.setProductId(dto.getIdProducto());
                    item.setQuantity(dto.getCantidad());
                    item.setTotal(dto.getTotal());
                    item.setActive(true);
                    return item;
                }).toList();
        purchase.setItems(items);
        return purchase;
    }
}
