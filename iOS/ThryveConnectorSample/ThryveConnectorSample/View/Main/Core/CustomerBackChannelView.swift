//
//  CustomerBackChannelView.swift
//  SampleCore
//
//  Created by Florian on 04.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct CustomerBackchannelView: View {
    let connector: CoreConnector

    @State private var value: String = ""
    @State private var enteredDataType: String = ""
    @State private var selectedValueType: Int = 0
    @State private var selectedDate: Date = Date()
    @State private var isConstant: Bool = false

    var body: some View {
        ScrollView {
            VStack(spacing: 16.0) {
                Toggle(isOn: $isConstant) {
                    Text("As constant value")
                }
                TextField("Enter the value", text: $value)
                    .modifier(TextFieldModifier())
                TextField("Enter the data type", text: $enteredDataType)
                    .modifier(TextFieldModifier())
                Text("Choose value type")
                Picker(selection: self.$selectedValueType, label: EmptyView()) {
                    ForEach(DataValueType.allCases) { element in
                        Text("\(element.type.string)")
                    }
                }
                if !self.isConstant {
                    Text("Choose a day")
                    DatePicker(selection: $selectedDate, displayedComponents: .date) {
                        EmptyView()
                    }
                }
                Button(action: {
                    let valueType = ValueType(rawValue: self.selectedValueType)!
                    let dataType = DataType(self.enteredDataType.integer!, valueType)

                    if self.isConstant {
                        let data = CustomerValue.CustomerPayload(value: self.value, of: dataType)
                        let customerValue = CustomerValue(data: [data])
                        self.connector.uploadConstantValue(data: customerValue) { (success, error) in
                            debugPrint(error ?? "", success)
                        }
                    } else {
                        let data = CustomerValue.CustomerPayload(
                            value: self.value,
                            of: dataType,
                            on: self.selectedDate
                        )
                        let customerValue = CustomerValue(data: [data])
                        self.connector.uploadDailyDynamicValue(data: customerValue) { (success, error) in
                            debugPrint(error ?? "", success)
                        }
                    }
                }, label: {
                    Text("Send value")
                })
                    .modifier(ButtonModifier())
            }
            .padding()
        }
    }
}
