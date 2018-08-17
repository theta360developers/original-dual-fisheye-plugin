# Dual-Fisheye For THETA V - 9 Image Bracket Shutter Speed Modification

Please see the master branch for more information.

## 9 Image Bracket Shooting

Using dual-fisheye plug-in to reduce shot time.

![9 image shutter speed](doc/img/9-image-shutter.png)

The original plug-in used exposure compensation.
This version uses shutter speed to achieve a wider range, from 1/25000 to
15 seconds. The THETA V can be hold the shutter open for 60 seconds. I have 
it set to 15 seconds to make testing easier.

## Post-Shoot Processing

Batch processed all images after the shoot to convert from dual-fisheye to
equirectangular.

![9 image stitched](doc/img/9-image-stitched.png)

## Create HDR File in Photoshop

Using HDR Pro.

![photoshop HDR merge](doc/img/9-image-hdr-merge.png)

## Save as Radiance HDR Format File

![photoshop save](doc/img/photoshop-save.png)

File size will increase from 3.3 MB to 47.5 MB due to luminosity information on
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

In 3 image mode, the LED will be magenta.

![9 image](doc/img/7-image-led.png)

Switch between the modes by briefly pressing the Wi-Fi button on the side
of the camera.

Discussion
https://community.theta360.guide/t/dual-fisheye-images-with-theta-v-plug-in/2692/