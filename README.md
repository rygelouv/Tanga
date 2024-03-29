<h1 align="center">Tanga Mobile App</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge&logo=appveyor"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=for-the-badge&logo=appveyor"/></a>
  <a href="https://github.com/rygelouv/Tanga/tree/dev"><img alt="API" src="https://img.shields.io/bitrise/af836c41-1d0e-4c07-a9e1-b4c4452a0686/dev?token=TmwPfWg3f5jHJEub8sA6Hw"/></a>
  <a href="https://sonarcloud.io/summary/new_code?id=rygelouv_Tanga"><img alt="API" src="https://sonarcloud.io/api/project_badges/measure?project=rygelouv_Tanga&metric=alert_status"/></a>
  <a href="https://codecov.io/gh/rygelouv/Tanga" ><img src="https://codecov.io/gh/rygelouv/Tanga/graph/badge.svg?token=LWTD8CBUBW"/> 
 </a>
</p>

![tanga_banner_v2_updated](https://github.com/rygelouv/Tanga/blob/dev/art/tanga_banner.png)


<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://sonarcloud.io/images/project_badges/sonarcloud-white.svg"/></a>
</p>

## 🚧 **This project is still under construction** 🚧
You can come back in a few months to see considerable progress. In the meantime, here are our recent achievements (this list is not always up to date)

- [Enabled more user-centric features such as search, summaries by categories, etc](https://github.com/users/rygelouv/projects/3/views/1)
- [Enabled Anonymous Authentication capabilities](https://github.com/users/rygelouv/projects/9)
- [Added more unit tests and UI tests](https://github.com/users/rygelouv/projects/7)
- [Worked on our test coverage infrastructure](https://github.com/users/rygelouv/projects/6)
- [Worked on some performance investigation and improvements](https://github.com/users/rygelouv/projects/10)

The other projects can be found here:  
- [Tanga Projects](https://github.com/rygelouv?tab=projects)

## Next steps on UI:
- We need to activate the remaning buttons on Summary details screen. Those are "Read", "Watch" and "Visualize"
- We need to work on the "weekly summary" logic for the home screen
- Issues should be investigated and fixed: https://github.com/rygelouv/Tanga/issues

## Infrastructure work
- [x] Add a CI (Bitrise and Github Actions)
- [x] Add Ktlint
- [x] Add Detekt
- [x] Add SonarCloud
- [x] Add Codecove for test coverage tracking
- [x] Add error tracking system with Sentry and Crashlytics
- [ ] Add Detekt Step to CI
- [ ] Add full Android build on Github Action workflow

## Testing
We still don't have test yet in the app. This Test project will start after the infrastructure work is done or at least the most part of it.
- [x] Add JUnit 5
- [X] Add Mockk 
- [x] Add Codecov for tracking project coverage
- [x] Add Kover and Jacoco for generating coverage reports
- [x] Start adding unit tests.
- [x] We need to UI test the screen composables
- [ ] Reach 50% coverage
- [ ] Add Screenshot tests
- [ ] Add Maestro tests

## Performances
- On debug build, the app is very slow on physical devices. This is need to be investigated. We started here: https://github.com/rygelouv/Tanga/pull/92
- [ ] Add macrobenchmark for Home screen
- [ ] Add baselie profiles if necessary
- [ ] Enable StrictMode to make sure no blocking work is done on the UI thread
- [ ] Track Memory leaks with Leak canary

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
