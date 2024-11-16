//package com.ticgrp10.WTFCINEMA.Controllers;
//
//import com.ticgrp10.WTFCINEMA.Services.BookingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/checkout")
//public class CheckoutController {
//
//    @Autowired
//    private BookingService bookingService;
//    @Autowired
//    private PurchaseSnackServices snackPurchaseService;
//
//    @GetMapping("/total")
//    public ResponseEntity<Map<String, Object>> getTotalPrice() {
//        double total = bookingService.getTotalBookingsPrice() + snackPurchaseService.getTotalSnackPrice();
//        Map<String, Object> response = new HashMap<>();
//        response.put("total", total);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/pagar")
//    public ResponseEntity<Map<String, Object>> realizarPago(@RequestBody Map<String, Object> request) {
//        boolean pagoConfirmado = (boolean) request.get("pagoConfirmado");
//
//        if (pagoConfirmado) {
//            try {
//                // Procesar el pago aquí (esto depende del sistema de pago que uses)
//                boolean pagoExitoso = procesarPago();
//
//                if (pagoExitoso) {
//                    // Limpiar las compras ya procesadas
//                    bookingService.finalizarReservas();
//                    snackPurchaseService.finalizarCompras();
//                    Map<String, Object> response = new HashMap<>();
//                    response.put("success", true);
//                    return ResponseEntity.ok(response);
//                } else {
//                    // En caso de que el pago falle
//                    cancelarProceso();
//                    Map<String, Object> response = new HashMap<>();
//                    response.put("success", false);
//                    return ResponseEntity.ok(response);
//                }
//
//            } catch (Exception e) {
//                // Manejar cualquier error en el proceso de pago
//                cancelarProceso();
//                Map<String, Object> response = new HashMap<>();
//                response.put("success", false);
//                return ResponseEntity.ok(response);
//            }
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Pago no confirmado"));
//    }
//
//    private boolean procesarPago() {
//        // Simulación del proceso de pago, esta lógica se debe conectar con un API de pagos
//        // Por ejemplo, Stripe, PayPal, etc.
//        return true; // O falso si el pago falla
//    }
//
//    private void cancelarProceso() {
//        // Si el pago falla, eliminamos las compras y reservas que están en proceso
//        bookingService.eliminarReservasEnProceso();
//        snackPurchaseService.eliminarComprasEnProceso();
//    }
//}

