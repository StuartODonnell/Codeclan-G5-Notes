# Deploying App on Android Device

## Learning Objectives

* Learn to get your Android app running on an Android device

Students might need to download and install [Android File Transfer](https://www.android.com/filetransfer/)

### Start App on Device

We want to run our app on our device, but we first need to make a couple of configuration changes on our device. We need to configure our Android device to allow us to use USB-debugging.

To do this, navigate to ```Settings > About phone/device```
Tap the Build Number **7** times and you should see the alert - You are now a developer!

When you go back to previous screen, you should now see Developer Options.

Turn on USB debugging and connect your device to your Mac.

To ensure everything is working correctly, we can use Android Debug Bridge (ADB).

ADB is useful for finding out the status of our app and devices.

In Android Studio, select ```Android Studio > Preferences > Appearance & Behaviour > System Settings > Android SDK```

Copy the path of the SDK (found in the ```Android SDK Location``` window).

In the Terminal use adb to list devices.

```
* ~ <SDK_path>/platform-tools/adb devices
```

We should be able to see the Emulator we are using.

Now, plug in your device and you will be prompted to confirm you trust this computer.

Click the checkbox and click ``OK``.

Run adb command again and you should see your connected device.

Click the green triangle again in Android Studio and you should see EightBall on your device!
