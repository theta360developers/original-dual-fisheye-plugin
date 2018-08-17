# Dual-Fisheye For THETA V - Bracket Shutter Speed Modification

Please see the master branch for more information.

## 12 Image Bracket Shooting

Using dual-fisheye plug-in to reduce shot time. There is a 
delay of approximately 1 second
between shots. 12 bracket shooting shows full range of shutter speed from
1/25000 to 60 seconds.

![12 image shutter speed](doc/img/12-image-shutter.png)

The original plug-in used exposure compensation.
This version uses shutter speed to achieve a wider exposure range.

You can switch this to 9 image bracket shooting or adjust the time spacing
between brackets in the code.

![9 image shutter speed](doc/img/9-image-shutter.png)

## Multi-Bracket On by Default

The plug-in will boot to multi-bracket mode by default. The original plug-in
started in single-shot mode.


## Post-Shoot Processing

Batch processed all images after the shoot to convert from dual-fisheye to
equirectangular.

![12 image stitched](doc/img/12-image-stitched.png)

## Create HDR File in Photoshop

Using HDR Pro.

![photoshop HDR merge](doc/img/photoshop-12-image.png)

## Save as Radiance HDR Format File

![photoshop save](doc/img/photoshop-save.png)

File size will increase from 5.35 MB to 49.9 MB due to luminosity information on
all images.

![Radiance file](doc/img/radiance-file.png)


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

In multi-shot image mode, the LED will be magenta.

![multi-shot image](doc/img/7-image-led.png)

Switch between the modes by briefly pressing the Wi-Fi button on the side
of the camera.

Discussion
https://community.theta360.guide/t/dual-fisheye-images-with-theta-v-plug-in/2692/