# lpu237_adr
* Current
  + version 3.2.0
  + funcationality
    - change interface.
    - buzzer on/off
    - i-button 4 mode(zeros,F12, zeros7, addmit and none(used definition))
    - i-button range function(none mode)
    - msr reading direction.
    - track orders
    - mmd1100 reset interval
    - track en/disable
    - msr global pre/postfix sending condition.
    - msr success indication condition.
    - firmware update with rom file.
    - supports callisto, ganymede and himalia.
  + NOT SUUPORT
    - langeuage change function.
    - firmware with raw binary file.
    - save the system parameters to file.
    - load the system parameters from file.

# history
* 2024.05.27 - version 3.2 release
  + change icon as google rule.
  + firmware update with rom file in android12.
  + fix buzzer bug of himalia.
  + test OK of ganymede v5.22.
  + test OK of himalia v2.2.( YOU MUST USE himalia v2.2 greater then equal.)
    - checked firmware update with lpu23x_00032.rom.
    - checked recovering system parameters after updating.
    - checked try firmware recover when app is started.

* 2024.05.24 - version 3.1.1 release
  + change for building apk in release.
  + build release test signed version apk

* 2024.05.24 - version 3.1 release
  + firmware update with rom file.
  + test OK of ganymede v5.22.
  + test OK of himalia v2.2.( YOU MUST USE himalia v2.2 greater then equal.)
    - checked firmware update with lpu23x_00032.rom.
    - checked recovering system parameters after updating.
    - checked try firmware recover when app is started.

* 2024.05.20 - developing
  + add i-button range function.
  + fix invalid rom file format error.
  + test OK of ganymede v5.22.

* 2024.05.17 - change IDE - Android Studio Jellyfish,2023.3.1
  + change SDK version minSdkVersion 25, targetSdkVersion 25, 
  + supports the defining i-button remove key.
  + supports separated i-button pre.post tag. 
* 2023.10.24 - change IDE - build test in Android Studio Giraffe 2022.3.1 Patch 2

* 2022.03.11 - version 3.0 release
  * bug fix - rom file check function.

* 2022.03.08 - version 2.0 release
  * bug fix - decoder detection.
  * change IDE - build in Android Studio Bumblebee 2021.1.1 Patch 2.

* 2018.7.14 - version 1.0 release
  * this project is for lpu237 magnetic card reader.
  * this android app can config and update the firmware of reader.
  * target - android v6.0.


===================================================================

Copyright (c) <2024> <Elpusk.Co.,Ltd>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
conditions: The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
