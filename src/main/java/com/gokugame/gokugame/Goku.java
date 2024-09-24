package com.gokugame.gokugame;



class Goku extends GameObject {

    //goku animation class that will render


    //goku animation images are added here to pass later to Characteranimation
    String[] gokuRunImages = {
            "src/main/resources/animation1.png",
            "src/main/resources/animation2.png",
            "src/main/resources/animation3.png",
            "src/main/resources/animation4.png",
    };

    String gokuJumpImage =  "src/main/resources/animation3.png";


    //goku animation class that will render
    private final CharacterAnimation gokuAnimation = new CharacterAnimation(gokuRunImages, gokuJumpImage);  // Initialize the animation

    public Goku(double x) {
        super(x, 480, 5);//goku Y is set here to 480
    }

    public Goku(double x,double speed,double width, double height) {
        super(x,480,speed,width,height);
    }

    public CharacterAnimation getGokuAnimation() {
        return gokuAnimation;
    }
}