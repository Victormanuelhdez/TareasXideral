package com.tarea.semana3.ejercicio1;
import java.util.*;
import java.util.stream.*;

public class Contact implements Comparable<Contact> {
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override
    public int compareTo(Contact other) {
        // TODO: orden natural por name (alfabetico)
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        // TODO: igualdad basada en email
        if (this == o) return true;
        if (!(o instanceof Contact c)) return false;
        return this.email.equals(c.email);
    }

    @Override
    public int hashCode() {
        // TODO: hash basado en email
        return email.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Contact{name='%s', email='%s', phone='%s'}",
                name, email, phone);
    }
}