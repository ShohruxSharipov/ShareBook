package com.hasan.sharebook.database

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID


class Database {
    companion object {
        val ref = Firebase.database.reference.child("users")
        val ref_1 = Firebase.database.reference.child("books")
        val ref_2 = Firebase.storage.reference.child("images")
        val ref_3 = Firebase.database.reference.child("requests")
        val users = FirebaseDatabase.getInstance().reference.child("users")
        val booksRef = FirebaseDatabase.getInstance().reference.child("books")
        val requestRef = FirebaseDatabase.getInstance().reference.child("requests")
        val images = FirebaseStorage.getInstance().reference.child("images")

        fun saveUser(context: Context, user: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", user)
            editor.apply()
        }

        fun getSavedUser(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            return sharedPreferences.getString("user", "") ?: ""
        }

        fun clearData(context:Context){
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
        }

        fun setUser(user: User) {
            user.id?.let { ref.child(it).setValue(user) }
        }

        fun checkUser(user: String, password: String, callback: (String) -> Unit) {
            users.child(user + "_123").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.getValue(User::class.java)!!.password == password) {
                        callback("Logged in Successfully")
                    } else {
                        callback("Incorrect Username or Password")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback("Database Error")
                }
            })
        }

        fun sendBook(book: Book) {
            ref_1.child(book.bookId!!).setValue(book)
        }

        fun sendBookPhoto(photo: Uri, book_id: String, context: Context) {

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(photo)
                ?.use { it.readBytes() }

            val unique_image_name = UUID.randomUUID()
            val storage = ref_2.child(book_id)
            byteArray.let {
                val uploadTask = storage.child("$unique_image_name.jpg").putBytes(it!!)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        fun getBooks(callback: (List<Book>) -> Unit) {
            booksRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = mutableListOf<Book>()
                    val children = snapshot.children
                    children.forEach {
                        val book = it.getValue(Book::class.java)
                        books.add(book!!)
                    }
                    callback(books)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }

            })
        }

        fun getBookPhotos(book_id: String, callback: (List<Uri>) -> Unit) {
            val res = mutableListOf<Uri>()
            images.child(book_id).listAll().addOnSuccessListener { items ->
                Log.d("TAG6", "getBookPhotos: $items")
                for (item in items.items) {
                    item.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downUri = task.result
                            res.add(downUri)
                        }
                    }
                }
            }
            callback(res)
        }

        fun sendRequest(request: Request) {
            val key = ref_3.push().key.toString()
            ref_3.child(key).setValue(request)
        }

        fun getBook(book_id: String, callback: (Book) -> Unit) {
            booksRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    children.forEach {
                        val book = it.getValue(Book::class.java)
                        if (book != null) {
                            if (book.bookId == book_id) {
                                callback(book)
                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(Book())
                }

            })
        }

        fun getCategorisedBooks(name: String, callback: (List<Book>) -> Unit) {
            booksRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = mutableListOf<Book>()
                    val children = snapshot.children
                    children.forEach {
                        val book = it.getValue(Book::class.java)
                        if (book!!.genre == name) {
                            books.add(book)
                        }
                        if (name == "Hammasi") {
                            books.add(book)
                        }
                    }
                    callback(books)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }

            })
        }

        fun getUser(user_id: String, callback: (User) -> Unit) {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    children.forEach {
                        val user = it.getValue(User::class.java)
                        if (user != null) {
                            if (user.id == user_id) {
                                callback(user)
                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(User())
                }

            })
        }

        fun getRequests(user: String, callback: (List<Request>) -> Unit) {
            requestRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val requests = mutableListOf<Request>()
                    val children = snapshot.children
                    children.forEach {
                        val request = it.getValue(Request::class.java)
                        if (user == request!!.to) {
                            requests.add(request)
                        }
                    }
                    Log.d("TAG2", "onDataChange: ${requests}")
                    callback(requests)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }

            })
        }

        fun updateBookOwner(book_id: String, updatedOwner: String) {
            ref_1.child(book_id).child("owner").setValue(updatedOwner)
        }

        fun deleteOneRequest() {
            ref_3.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    children.forEach {
                        val item =it.getValue(Request::class.java)
                        if (item!!.from== item.to) {
                            Log.d("11", "onDataChange: ${it.getValue(Request::class.java)}")
                            ref_3.child(it.key!!).removeValue()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        fun updateRequest(request: Request) {
            var book = Book()
            getBook(request.bookId!!) {
                book = it
            }
            ref_3.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    children.forEach {
                        val item = it.getValue(Request::class.java)
                        if (item!!.bookId == request.bookId) {
                            item.to = book.owner
                            ref_3.child(it.key!!).setValue(item)
                        }
                    }
                    deleteOneRequest()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}