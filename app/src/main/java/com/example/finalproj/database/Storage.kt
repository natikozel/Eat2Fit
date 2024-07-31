package com.example.finalproj.database

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

fun uploadImageToFirebase(uri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
    val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")

    imageRef.putFile(uri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}