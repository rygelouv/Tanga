<h1 align="center">Tanga Mobile App</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge&logo=appveyor"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=for-the-badge&logo=appveyor"/></a>
</p>

![tanga_banner_v2_updated](https://user-images.githubusercontent.com/7549316/227764073-7e88f504-710e-46a6-9a43-e18521200f4d.png)


## 🚧 **This project is still under construction** 🚧
You can come back in a few months to see considerable progress. 
We finished implementing most screen from the design with dummy data. We are now making it functional by working with real data from firebase firestore and cloud storage.
So far we have done:
- Home screen: now shows real summaries broken by sections based on categories
- Search screen: now allows you to search for summaries and filter by category
- Summary Details screen: show the real detailed information of the summary selected
- Audio Player: now the screen can play the audio of the summary selected
- We have done the most part of the error management system. But some screens still need some work regarding errors

## Next steps on UI:
- We need to activate the remaning buttons on Summary details screen. Those are "Read", "Watch" and "Visualize"
- We need to enable the "see all" buttons on home screen. Clicking on them opens up a new screen with all summaries for the category selected
- We need to work on the "daily summary" logic for the home screen
- We need to enable click on summary from the search screen
- Issues should be investigated and fixed: https://github.com/rygelouv/Tanga/issues

## Infrastructure work
- [ ] Add a CI (Bitrise)
- [ ] Add Spotless and ktlint
- [ ] Add Detekt
- [ ] Add SonarCloud
- [ ] Add error tracking system
- More...

## Testing
We still don't have test yet in the app. This Test project will start after the infrastructure work is done or at least the most part of it.
- We need to add JUnit 5
- We need to add unit tests for most repositories, interactors, viewmodels and other components
- We need to UI test the screen composables
  
# License
```xml
 Copyright 2023 Rygel Louv

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

```
