import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        val inputName = findViewById<EditText>(R.id.inputName)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLoad = findViewById<Button>(R.id.btnLoad)
        val output = findViewById<TextView>(R.id.output)

        // Guardar en Firestore
        btnSave.setOnClickListener {
            val name = inputName.text.toString()
            if (name.isNotEmpty()) {
                val user = hashMapOf("name" to name)

                db.collection("users").add(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Leer desde Firestore
        btnLoad.setOnClickListener {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    val names = StringBuilder()
                    for (doc in result) {
                        val name = doc.getString("name")
                        names.append(name).append("\n")
                    }
                    output.text = names.toString()
                }
                .addOnFailureListener {
                    output.text = "Error al leer datos: ${it.message}"
                }
        }
    }
}
