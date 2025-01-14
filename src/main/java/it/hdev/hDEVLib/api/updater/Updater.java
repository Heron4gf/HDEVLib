package it.hdev.hDEVLib.api.updater;

public interface Updater {


    void onNewUpdateAvailable(String version, String downloadLink);
}
