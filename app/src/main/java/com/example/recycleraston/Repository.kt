package com.example.recycleraston

import io.github.serpro69.kfaker.Faker

class Repository {
    private var lastContactId = 0
    private val faker = Faker()

    fun createContactsList(numContacts: Int): ArrayList<ContactsInfo> {
        faker.unique.configuration {
            enable(faker::idNumber)
        }
        val contacts: ArrayList<ContactsInfo> = ArrayList()
        for (i in 1..numContacts) {
            contacts.add(ContactsInfo(
                ++lastContactId,
                faker.name.firstName(),
                faker.name.lastName(),
                faker.phoneNumber.phoneNumber()))
        }
        return contacts
    }
}