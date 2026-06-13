package com.tarea.semana2.ejercicio4;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProductPipeline {

    private Predicate<Product> filter = product -> true;
    private Function<Product, String> transform =
            Product::toDisplayString;

    public ProductPipeline where(
            Predicate<Product> predicate
    ) {
        this.filter = this.filter.and(predicate);
        return this;
    }

    public ProductPipeline transform(
            Function<Product, String> function
    ) {
        this.transform = function;
        return this;
    }

    public void forEach(
            List<Product> products,
            Consumer<String> action
    ) {
        for (Product product : products) {
            if (filter.test(product)) {
                action.accept(transform.apply(product));
            }
        }
    }

    public long count(List<Product> products) {
        long total = 0;

        for (Product product : products) {
            if (filter.test(product)) {
                total++;
            }
        }

        return total;
    }
}