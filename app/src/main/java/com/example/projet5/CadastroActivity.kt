package com.example.projet5

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.db.insert

//import kotlinx.android.synthetic.main.activity_cadastro.*
///import kotlinx.android.synthetic.main.list_view_item.*


class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            val qtde = txtQtde.text.toString()
            val valor = txtValor.text.toString()
            val senha = txtSenha.text.toString()
            val espera = txtEsperando.text.toString()

            if (produto.isNotEmpty() && qtde.isNotEmpty() && valor.isNotEmpty() && senha.isNotEmpty() && (senha == "Cristiano")) {
                txtEsperando.text = "Item Adicionado"

                    database.use {
                        val idProduto = insert(
                        "Produtos",
                        "nome" to produto,
                        "quantidade" to qtde,
                        "valor" to valor.toDouble(),
                        "foto" to imageBitMap?.toBYteArray()
                        )

                        if (idProduto != -1L) {
                            toast("Item inserido com sucesso")
                            txtProduto.text.clear()
                            txtQtde.text.clear()
                            txtValor.text.clear()
                            txtSenha.text.clear()
                        }else{
                        toast("Erro ao inserir no banco de dados")
                        }
                    }
            }else{
                txtEsperando.text = "digite senha correta..."
                txtSenha.error = "Senha Inválida"
                txtProduto.error =
                    if (txtProduto.text.isEmpty()) "Preencha o nome do Produto" else null
                txtQtde.error = if (txtQtde.text.isEmpty()) "Preencha a quantidade" else null
                txtValor.error = if (txtValor.text.isEmpty()) "Preecha o valor" else null

            }
        }

        imgFotoProduto.setOnClickListener {
            abrirGaleria()
        }
    }

    fun abrirGaleria() {
        //definindo a ação de conteudo
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //definindo filtro para imagens
        intent.type = "image/*"

        //inicializando a activity com resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)

    }
}