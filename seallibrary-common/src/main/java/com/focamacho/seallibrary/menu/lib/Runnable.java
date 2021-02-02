package com.focamacho.seallibrary.menu.lib;

public interface Runnable {

    /**
     * Runnable contendo informações
     * do click efetuado em um inventário.
     */
    interface ClickRunnable {
        void run(IClick click);
    }

}
