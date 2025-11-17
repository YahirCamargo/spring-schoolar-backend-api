package mx.tecnm.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mx.tecnm.backend.api.model.Producto;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/hello")
    public String home() {
        return "Hola API rest";
    }

    @GetMapping("/producto")
    public Producto getProducto() {
        Producto p = new Producto();
        p.nombre="Coca cola";
        p.precio= 13.5;
        p.codigoBarras= "327498223423";
        return p;
    }

    @GetMapping("/productos")
    public Producto[] getProductos() {
        Producto p1 = new Producto();
        p1.nombre="Coca cola";
        p1.precio= 13.5;
        p1.codigoBarras= "327498223423";

        Producto p2 = new Producto();
        p2.nombre="Pepsi";
        p2.precio= 17.5;
        p2.codigoBarras= "3272398223423";

        Producto p3 = new Producto();
        p3.nombre="Fanta";
        p3.precio= 16.5;
        p3.codigoBarras= "389723";
        return new Producto[]{p1,p2,p3};
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductosId(@PathVariable int id) {
        if(id<0 || id>2){
            return ResponseEntity.notFound().build();
        }
        Producto productos[] = new Producto[3];
        Producto p1 = new Producto();
        p1.nombre="Coca cola";
        p1.precio= 13.5;
        p1.codigoBarras= "327498223423";
        productos[0]=p1;

        Producto p2 = new Producto();
        p2.nombre="Pepsi";
        p2.precio= 17.5;
        p2.codigoBarras= "3272398223423";
        productos[1]=p2;

        Producto p3 = new Producto();
        p3.nombre="Fanta";
        p3.precio= 16.5;
        p3.codigoBarras= "389723";
        productos[2]=p3;

        Producto productoEncontrado = productos[id];
        return ResponseEntity.ok(productoEncontrado);
    }

}
