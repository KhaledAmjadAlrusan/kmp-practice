import SwiftUI

import Shared

@main
struct iOSApp: App {
    
    init () {
        KoinKt.doInitKoinIOS()
    }
    
    var body: some Scene {
        WindowGroup {
            ListView()
        }
    }
}
