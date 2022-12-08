import SwiftUI

import shared
@main
struct iOSApp: App {
    /**
     you can also initialise koin using an app delegate
     */
    init(){
        KoinModuleKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
