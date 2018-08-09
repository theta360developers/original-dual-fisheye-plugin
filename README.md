# Dual-Fisheye For THETA V

This is a code for the Dual Fisheye Plug-in by Ichi Hirota for the RICOH THETA V.
This repository was created for a technical workshop as part of educational curriculum. 
This code is not intended for production use.  

For more information on
plug-in development, including use and modification of this plug-in, see the free plug-in developer
guide, available at http://theta360.guide/plugin/

Ichi Hirota's app should be available in the THETA Plug-in Store shortly.

## Features and Usage Information

- 3 image dual-fisheye taken within 3 seconds
- Each image takes approximately 1 second
- single image dual-fisheye in less than 1 second
- HDR blending is handled by a free third-party application called Picturenaut (see documentation for instructions)
- Dual-fisheye to equirectangular stitching is handled by Ichi Hirota's app. Demo version available in documentation.

## Benefits of This Technique

### High Volume

By saving the images as dual-fisheye, the camera can take a picture once per second, instead of 4 seconds. This is good for high-volume image taking such as at a large
construction or retail store site. The pictures can be stitched later in a central
office.

### HDR

Normally, a 3 image HDR picture will take 12 seconds. With a dual-fisheye technique, 3 images can be taken in 12 seconds. 

### Industrial and Scientific Applications

Some developers prefer to use dual-fisheye images.
For example, people involved with object 
recognition and external sensor alignment use dual-fisheye images.

## Repository Organization

    /apk/app-debug.apk - binary to install in the RICOH THETA V
    /tools/stitcher/MiSphereConverter_for_THETAV.apk - mobile stitching application for your Android phone
    /app/src/main/java/com/theta360/pluginapplication/MainActivity.java - main plug-in source code

## Screenshots

Dual-fisheye Image

![merged image](doc/img/merged-image.jpg)

Stitched Image

![stitched image](doc/img/stitched-image.jpg)

Closeup of Stitch Line

![closeup](doc/img/closeup.jpg)


RICOH Camera API for plug-in API for exposure compensation is available at:
https://api.ricoh/docs/theta-plugin-reference/camera-api/

The Ricoh Plug-in SDK is available at:
https://github.com/ricohapi/theta-plugin-sdk

## Usage Notes

The plug-in is called *Plugin Application*

You must set permissions. Documentation includes information on using Vysor.

![set permissions](doc/img/set-permissions.jpg)

In single image mode, the Wi-Fi LED will be cyan.

![single image](doc/img/single-image-led.png)

In 3 image mode, the LED will be magenta.

![3 image](doc/img/7-image-led.png)

Switch between the modes by briefly pressing the Wi-Fi button on the side
of the camera.

Discussion
https://community.theta360.guide/t/dual-fisheye-images-with-theta-v-plug-in/2692/