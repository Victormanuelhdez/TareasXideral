package com.tarea.semana3.ejercicio1;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class ContactManager {

    private final Set<Contact> contacts = new TreeSet<>();

    public boolean addContact(Contact contact) {
        return contacts.add(contact);
    }

    public Optional<Contact> findByEmail(String email) {
        return contacts.stream()
                .filter(contact -> contact.getEmail().equals(email))
                .findFirst();
    }

    public List<Contact> findByNamePrefix(String prefix) {
        return contacts.stream()
                .filter(contact -> contact.getName()
                        .toLowerCase()
                        .startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Contact> getAllSortedBy(Comparator<Contact> comp) {
        return contacts.stream()
                .sorted(comp)
                .collect(Collectors.toList());
    }

    public int size() {
        return contacts.size();
    }

    public static void main(String[] args) {
        ContactManager mgr = new ContactManager();

        System.out.println("=== Agregando Contactos ===");

        System.out.println("Ana: " + mgr.addContact(
                new Contact(
                        "Ana Garcia",
                        "ana@mail.com",
                        "555-1111"
                )
        ));

        System.out.println("Luis: " + mgr.addContact(
                new Contact(
                        "Luis Lopez",
                        "luis@mail.com",
                        "555-2222"
                )
        ));

        System.out.println("Maria: " + mgr.addContact(
                new Contact(
                        "Maria Torres",
                        "maria@mail.com",
                        "555-3333"
                )
        ));

        System.out.println("Ana duplicada: " + mgr.addContact(
                new Contact(
                        "Ana Garcia",
                        "ana@mail.com",
                        "555-9999"
                )
        ));

        System.out.println("Carlos: " + mgr.addContact(
                new Contact(
                        "Carlos Ruiz",
                        "carlos@mail.com",
                        "555-4444"
                )
        ));

        System.out.println("Total contactos: " + mgr.size());

        System.out.println("\n=== Orden Natural (por nombre) ===");

        mgr.getAllSortedBy(Comparator.naturalOrder())
                .forEach(System.out::println);

        System.out.println("\n=== Ordenados por Email ===");

        mgr.getAllSortedBy(
                        Comparator.comparing(Contact::getEmail)
                )
                .forEach(System.out::println);

        System.out.println("\n=== Buscar por Email ===");

        mgr.findByEmail("maria@mail.com")
                .ifPresentOrElse(
                        contact ->
                                System.out.println("Encontrado: " + contact),
                        () ->
                                System.out.println("No encontrado")
                );

        mgr.findByEmail("noexiste@mail.com")
                .ifPresentOrElse(
                        contact ->
                                System.out.println("Encontrado: " + contact),
                        () ->
                                System.out.println("No encontrado")
                );

        System.out.println("\n=== Buscar por Prefijo 'ma' ===");

        mgr.findByNamePrefix("ma")
                .forEach(System.out::println);
    }
}