package com.tarea.semana2.ejercicio4;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ProductCatalog {

    public static void main(String[] args) {
        Supplier<List<Product>> catalogSupplier = () -> List.of(
                new Product(
                        "Laptop",
                        999.99,
                        "Electronica",
                        true
                ),
                new Product(
                        "Mouse",
                        29.99,
                        "Electronica",
                        true
                ),
                new Product(
                        "Teclado",
                        79.99,
                        "Electronica",
                        false
                ),
                new Product(
                        "Camisa",
                        39.99,
                        "Ropa",
                        true
                ),
                new Product(
                        "Java Book",
                        49.99,
                        "Libros",
                        true
                ),
                new Product(
                        "Monitor",
                        349.99,
                        "Electronica",
                        true
                )
        );

        List<Product> catalogo = catalogSupplier.get();

        System.out.println("=== Catalogo Completo ===");

        catalogo.stream()
                .map(Product::toDisplayString)
                .forEach(System.out::println);

        System.out.println(
                "\n=== Pipeline: Electronica en stock, precio > $50 ==="
        );

        Predicate<Product> electronica =
                product -> product.category().equals("Electronica");

        Predicate<Product> precioMayorA50 =
                product -> product.price() > 50;

        new ProductPipeline()
                .where(Product::isAvailable)
                .where(electronica.and(precioMayorA50))
                .forEach(catalogo, System.out::println);

        System.out.println(
                "\n=== Pipeline: Disponibles, precio < $100 ==="
        );

        ProductPipeline pipeline = new ProductPipeline()
                .where(Product::isAvailable)
                .where(product -> product.price() < 100)
                .transform(
                        product ->
                                "  * "
                                        + product.name().toUpperCase()
                                        + " - $"
                                        + product.price()
                );

        pipeline.forEach(catalogo, System.out::println);

        System.out.println(
                "\n=== Pipeline: Ropa o Libros ==="
        );

        Predicate<Product> ropa =
                product -> product.category().equals("Ropa");

        Predicate<Product> libros =
                product -> product.category().equals("Libros");

        new ProductPipeline()
                .where(ropa.or(libros))
                .forEach(catalogo, System.out::println);

        System.out.println(
                "\nTotal disponibles: "
                        + new ProductPipeline()
                        .where(Product::isAvailable)
                        .count(catalogo)
        );
    }
}