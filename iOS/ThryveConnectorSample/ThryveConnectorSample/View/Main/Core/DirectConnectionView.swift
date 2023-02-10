//
//  DirectConnectionView.swift
//  SampleCore
//
//  Created by Florian on 04.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct DirectConnectionView: View {
    let connector: CoreConnector

    @State private var isPresented = false
    @State private var isDirectConnection = true
    @State private var isInstantRevoke = false
    @State private var selectedTrackerId = 1

    var body: some View {
        VStack {
            Toggle(isOn: $isDirectConnection) {
                Text("Direct connection")
            }
            Toggle(isOn: $isInstantRevoke) {
                Text("Instant revoke")
            }
            .disabled(isDirectConnection)
            Text("Choose data source")
                .fontWeight(.bold)
            List(DataSource.allCases) { element in
                HStack(spacing: 30.0) {
                    Image("logo_of_\(element.name!)")
                    Spacer()
                    Button(action: {
                        selectedTrackerId = element.id
                        isPresented = true
                    }, label: {
                        Text("\(isDirectConnection ? "Connect" : "Revoke")")
                    })
                    .padding()
                    .modifier(ButtonModifier(width: 200))
                    Spacer()
                }
            }
        }
        .sheet(isPresented: $isPresented) {
            DirectConnectionWebViewController(
                connector: connector,
                connection: isDirectConnection,
                instantRevoke: isInstantRevoke,
                source: Tracker(nil, selectedTrackerId)
            )
        }
        .padding()
        .navigationBarTitle(Text("Direct connection"))
    }
}
