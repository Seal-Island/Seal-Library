package com.focamacho.seallibrary.menu.lib;

public interface Runnable {

    /**
     * Runnable contendo informações
     * do click efetuado em um inventário.
     */
    interface ClickRunnable {
        void run(AbstractClick click);
    }

    /**
     * Runnable contendo informações
     * da interação efetuada em um inventário.
     */
    interface InteractRunnable {
        void run(AbstractInteract interact);
    }

}
