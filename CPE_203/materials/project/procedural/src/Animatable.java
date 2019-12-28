	interface Animatable
{
   void nextImage();
   Action createAnimationAction(int repeatCount);
   int getAnimationPeriod();
}
