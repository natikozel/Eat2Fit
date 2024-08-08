package com.example.finalproj.database

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object StorageManager {
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val storageRef: StorageReference by lazy {
        storage.reference
    }

    fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val imageRef: StorageReference = storageRef.child("images/${uri.lastPathSegment}")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    fun getImageDownloadUrl(imagePath: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val imageRef: StorageReference = storageRef.child(imagePath)
        imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
            onSuccess(downloadUri.toString())
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    fun deleteImage(imagePath: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val imageRef: StorageReference = storageRef.child(imagePath)
        imageRef.delete().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}