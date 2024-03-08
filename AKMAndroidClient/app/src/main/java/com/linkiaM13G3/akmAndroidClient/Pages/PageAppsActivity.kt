package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.R


class PageAppsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_apps_list)

        val buttonBack = findViewById<Button>(R.id.btn_backArrowpsw)
        buttonBack.setOnClickListener {
            val intent = Intent(this, PageMain::class.java)
            startActivity(intent)
        }

        // esta linea encuentra el RV en el xml lo asigana a la variable
        val recyclerView = findViewById<RecyclerView>(R.id.rvOptions)

        //Aqui asignamos un LinearLayout para posoicionar los elementos en la lista de forma linea,
        //uno ddebajo de otro
        recyclerView.layoutManager = LinearLayoutManager(this)

        //establecemos un adaptador para el RV, utilizando la clase AdapterAppList que hemos creado
        recyclerView.adapter = AdapterAppList(getAppsList(), this)


    }

    fun onAppClick(appName: String) {
        // Inicia la actividad KeyList y pasa el nombre de la aplicación como extra
        val intent = Intent(this, PagePwdMain::class.java)
        intent.putExtra("APP_NAME_EXTRA", appName)
        startActivity(intent)
    }

    private fun getAppsList(): List<AdapterAppList.AppsList> {
        return listOf(
            AdapterAppList.AppsList(
                "Google",
                "url_to_google_logo",
                "drawable_name_for_arrow_icon" ,
               ""
            ),
            AdapterAppList.AppsList(
                "Facebook",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""
            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
            AdapterAppList.AppsList(
                "Amazon",
                "url_to_facebook_logo",
                "drawable_name_for_arrow_icon" ,
                ""

            ),
        )
    }
}