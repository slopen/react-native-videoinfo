# Example React Native / Video Picker / addActivityEventListener Module

Opens video gallery, on file selection returns

`duration` - MediaStore.Video.Media.DURATION

`dateTaken` - MediaStore.Video.Media.DATE_TAKEN

`mimeType` - MediaStore.Video.Media.MIME_TYPE

`path` - MediaStore.Video.Media.DATA (absolute videofile path)

`resolution` - MediaStore.Video.Media.RESOLUTION

`size` - MediaStore.Video.Media.SIZE

`title` - MediaStore.Video.Media.TITLE


```
import {NativeModules} from 'react-native';

const {VideoInfo} = NativeModules;

VideoInfo
    .launchVideoLibrary ({})
    .then ((result) => {
        console.log ('result', result);
    })
    .catch ((err) => {
        console.error ('error', err);
    });

```
