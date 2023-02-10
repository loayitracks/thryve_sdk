//
//  MainView.swift
//  SampleCore
//
//  Created by Florian on 01.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK
import CoreBluetooth

struct MainView: View {
    var body: some View {
        TabView {
            CoreView(viewModel: CoreViewModel())
                .tabItem {
                    Image("con")
                    Text("Core")
            }
            RecognitionView(viewModel: RecognitionViewModel())
                .tabItem {
                    Image("rec")
                    Text("Recognition")
            }
            AppleHealthView(viewModel: AppleHealthViewModel())
                .tabItem {
                    Image("heart")
                    Text("Apple Health")
            }
            BGMSourceListView()
                .tabItem {
                    Image("blood")
                    Text("BGM")
            }
        }
    }

}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
