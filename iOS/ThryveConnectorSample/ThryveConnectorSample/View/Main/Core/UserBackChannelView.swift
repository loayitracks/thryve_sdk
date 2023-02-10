//
//  UserBackChannelView.swift
//  SampleCore
//
//  Created by Florian on 11.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct UserBackChannelView: View {
    let connector: CoreConnector

    @State private var height: String = ""
    @State private var weight: String = ""
    @State private var birthdate: Date = Date()
    @State private var selectedGender: Int = 0

    var body: some View {
            ScrollView {
                VStack(spacing: 16.0) {
                    TextField("Enter the height", text: $height)
                    .modifier(TextFieldModifier())
                    TextField("Enter the weight", text: $weight)
                    .modifier(TextFieldModifier())
                    Text("Choose birthdate")
                    DatePicker(selection: $birthdate, displayedComponents: .date) {
                        EmptyView()
                    }
                    Text("Choose gender")
                    Picker(selection: self.$selectedGender, label: EmptyView()) {
                        ForEach(GenderData.allCases) { element in
                            Text("\(element.name), index: \(element.id)")
                        }
                    }
                    Button(action: {
                        let user = User(
                            height: self.height.integer,
                            weight: self.weight.double,
                            birthdate: self.birthdate,
                            gender: Gender.init(rawValue: self.selectedGender)
                        )
                        self.connector.uploadUserInformation(data: user) { (success, error) in
                            debugPrint(error ?? "", success)
                        }
                    }, label: {
                        Text("Send user information")
                    })
                    .modifier(ButtonModifier())
                }
                .padding()
            }
    }
}
