package com.jogo.fasterthanlight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FasterThanLight extends ApplicationAdapter {

    private Texture carro,fundo,segundoFundo;
	private ShapeRenderer shape;
    public static SpriteBatch batch;
    private Texture btl,btr,btu,btd;
    private Boolean apertouCima = false;


    //Atributos
    private float larguraDispositivo,alturaDispositivo;
    private float movimentoDoFundo,movimentoDoSegundoFundo;
    private float posicaoVerticalCarroPrincipal;
    private float posicaoHorizontalCarroPrincipal;
    private float deltaTime;
    private int estadoDoJogo;
    private int pontuacao;
    private BitmapFont pontos;

    //Câmera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 800;
    private final float VIRTUAL_HEIGHT = 600;




	@Override
	public void create () {

        carro = new Texture("carroteste.png");
        fundo = new Texture("fundo.jpg");
        segundoFundo = new Texture("fundo.jpg");
        btd = new Texture("flatDark26.png");
        btu = new Texture("flatDark25.png");
        btl = new Texture("flatDark23.png");
        btr = new Texture("flatDark24.png");


        pontos = new BitmapFont();
        pontos.setColor(Color.WHITE);
        pontos.getData().setScale(5);

        shape = new ShapeRenderer();
        batch = new SpriteBatch();



        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH /2,VIRTUAL_HEIGHT /2,0);
        viewport = new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);

        /*larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        */
        movimentoDoFundo = 0;
        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;

        posicaoHorizontalCarroPrincipal = larguraDispositivo /2 + carro.getWidth() /2;
        posicaoVerticalCarroPrincipal =   0;
        estadoDoJogo = 0;
        pontuacao =0;

	}

	@Override
	public void render () {

        camera.update();
        shape.setProjectionMatrix(batch.getProjectionMatrix());

        deltaTime = Gdx.graphics.getDeltaTime();






        movimentoDoFundo -= deltaTime * 100;
        movimentoDoSegundoFundo -= deltaTime *100;


       //Fundo infinito
        if(movimentoDoFundo <- alturaDispositivo){
            movimentoDoFundo = alturaDispositivo;
        }
        if(movimentoDoSegundoFundo < - alturaDispositivo - alturaDispositivo){
            movimentoDoSegundoFundo = movimentoDoFundo;
        }



        //Impossibilita o carro de sair da tela
        if(posicaoVerticalCarroPrincipal <= 0){
            posicaoVerticalCarroPrincipal =0;
        }
        if(posicaoVerticalCarroPrincipal >= 600 - carro.getHeight()  ){
            posicaoVerticalCarroPrincipal = 600 - carro.getHeight() ;
        }
        if(posicaoHorizontalCarroPrincipal <=255 - carro.getWidth()){
            posicaoHorizontalCarroPrincipal =255 - carro.getWidth();
        }
        if(posicaoHorizontalCarroPrincipal >= 600 - carro.getWidth()){
            posicaoHorizontalCarroPrincipal = 600 - carro.getWidth();
        }



       Rectangle bounds = new Rectangle(60,alturaDispositivo /2,90,90);
       Rectangle boundsDown = new Rectangle(680,alturaDispositivo /2,90,90);
        Rectangle boundsLeft = new Rectangle(60,30,90,90);
        Rectangle boundsRight = new Rectangle(680,30,90,90);
        Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        camera.unproject(tmp);


        if(bounds.contains(tmp.x,tmp.y)){
                posicaoVerticalCarroPrincipal += 2;
        }
        if(boundsDown.contains(tmp.x,tmp.y)){
            posicaoVerticalCarroPrincipal -= 2;

        }
        if(boundsRight.contains(tmp.x,tmp.y)){
            posicaoHorizontalCarroPrincipal += 2;
        }
        if(boundsLeft.contains(tmp.x,tmp.y)){
            posicaoHorizontalCarroPrincipal -= 2;
        }




        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(fundo,0,movimentoDoFundo,larguraDispositivo,alturaDispositivo);
        batch.draw(fundo,0,movimentoDoSegundoFundo + alturaDispositivo,larguraDispositivo,alturaDispositivo);
        batch.draw(carro,posicaoHorizontalCarroPrincipal,posicaoVerticalCarroPrincipal);
        pontos.draw(batch,String.valueOf(pontuacao),larguraDispositivo - 60,alturaDispositivo - 30);
        batch.draw(btd,680,alturaDispositivo /2,90,90);
        batch.draw(btu,60,alturaDispositivo /2,90,90);
        batch.draw(btl,60,30,90,90);
        batch.draw(btr,680,30,90,90);


        batch.end();



	}
	
	@Override
	public void dispose () {

	}
    public void resize(int width, int height) {
        viewport.update(width,height);

   }

}
