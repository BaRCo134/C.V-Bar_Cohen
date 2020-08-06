package com.example.catchthefrog.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.catchthefrog.R;

public class GameObjectView extends AppCompatImageButton {

    private ConstraintLayout.LayoutParams layoutParams;
    private GameObject gameObject = null;

    public GameObjectView(Context context) {
        super(context);
    }

    public GameObjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameObjectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        if (this.gameObject != null){
            switch (gameObject.getType()){
                case Frog:
                    Drawable myDrawable = getResources().getDrawable(R.drawable.frog);
                    setImageDrawable(myDrawable);
                    break;
                case Bomb:
                    setImageDrawable(getResources().getDrawable(R.drawable.bomb));
                    break;
            }
        }
        layoutParams = new ConstraintLayout.LayoutParams((int)gameObject.getSize(), (int)gameObject.getSize());
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;//change when hebrew?
        setLayoutParams(layoutParams);

        setOnClickListener(v -> gameObject.setClick());
    }

    public void onUpdate(){
        if (gameObject != null){
            layoutParams.topMargin = (int)gameObject.getPosition().y;
            layoutParams.leftMargin = (int)gameObject.getPosition().x;
            setLayoutParams(layoutParams);
        }
    }
}
