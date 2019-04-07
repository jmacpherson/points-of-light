# Points of Light
### An interview project

###### Compilation

Requires environment variable **API_KEY_500PX** to be set with a valid API Key in build environment, and to be built from the CLI as Android Studio will not pick up Environment variables on its own.


###### Architecture

- Follows MVVM pattern.
- Networking handled by NetworkRequestRunner.
- Navigation handled by NavigationManager.
- PhotoUtils determines size of images to request, and optimal size to display based on main content resolution.
- PhotoAdapter presets ImageView to dimensions of photo at chosen scale in order to smooth out scrolling and prevent excess photo loading requests via Picasso


###### Todo:

- refactor Photo model and PhotoUtils to support preferred scale for different resolutions rather than the resolution in use the first time the image is loaded
- animate presentation of next page button
- trim down Photo model to only fields that are being used
- display comments in Lightbox view
- style Gallery view
- increase unit test coverage