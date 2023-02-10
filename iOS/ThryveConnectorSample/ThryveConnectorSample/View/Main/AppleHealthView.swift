//
//  AppleHealthView.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK
import HealthKit
import ModuleAppleHealth

struct AppleHealthView: View, ViewProtocol {
    private let localNotificationManager = LocalNotificationManager()
    @ObservedObject var viewModel: AppleHealthViewModel
    @State private var userInformation = ""
    @State var isSetDataTypes: Bool = false
    @State private var isAlertPresented = false
    @State var types: Set<HKObjectType> = Set()
    @State var connectionLog: String = ""
    @State private var showSynchronise = false
    @State private var synchEpoch = false
    @State private var synchDaily = false

    var body: some View {
        NavigationView {
            VStack(spacing: 16.0) {
                Image("mainLogo")
                Spacer()
                Button(action: {
                    let authorizeStart = CFAbsoluteTimeGetCurrent()
                    if types.isEmpty {
                        types = HKConnectorType.allTypes
                    }
                    connectionLog = ""
                    viewModel
                            .connector
                            .authorizeHealthKit(types: types) { response in
                        Logger.i("authorizeHealthKit: \(response.successful)")
                        let authorizeDiff = CFAbsoluteTimeGetCurrent() - authorizeStart
                        connectionLog += "authorizeHealthKit: Took \(String(format: "%.2f", authorizeDiff)) seconds.\n"
                        Logger.e(String(describing: response.error))
                        let startHKStart = CFAbsoluteTimeGetCurrent()
                        // In case of client error, make sure the type list
                        // is filtered -> some permissions could have been denied.
                        viewModel
                                .connector
                                .startHealthKitIntegration(
                                types: types
                        ) { (response) in
                            let startHKDiff = CFAbsoluteTimeGetCurrent() - startHKStart
                            Logger.i("startHealthKitIntegration: \(response.successful)")
                            connectionLog += "startHealthKitIntegration: Took \(String(format: "%.2f", startHKDiff)) seconds.\n"
                            isAlertPresented = true
                            self.localNotificationManager.scheduleNotification(
                                    Notification(
                                            title: "startHealthKitIntegration:",
                                            subtitle: "Connected => \(response.successful)"
                                    )
                            )
                            Logger.e(String(describing: response.error))
                            showUserData()
                        }
                    }
                }, label: {
                    Text("Start HK integration")
                }).modifier(ButtonModifier())
                Button(action: {
                    let stopHKstart = CFAbsoluteTimeGetCurrent()
                    viewModel.connector.stopHealthKitIntegration { (response) in
                        Logger.i("stopHealthKitIntegration: \(response.successful)")
                        let stopHKDiff = CFAbsoluteTimeGetCurrent() - stopHKstart
                        connectionLog += "stopHealthKitIntegration: Took \(String(format: "%.2f", stopHKDiff)) seconds.\n"
                        self.localNotificationManager.scheduleNotification(
                                Notification(
                                        title: "stopHealthKitIntegration:",
                                        subtitle: "\(response.successful)"))
                        Logger.e(String(describing: response.error))
                        showUserData()
                    }
                }, label: {
                    Text("Stop HK integration")
                }).modifier(ButtonModifier())
                Button("Synchronize Epoch") {
                    synchEpoch.toggle()
                }.modifier(ButtonModifier())
                        .sheet(isPresented: $synchEpoch) {
                            SheetView(type: 0, connector: viewModel.connector, view: self)
                        }
                Button("Synchronize Daily") {
                    synchDaily.toggle()
                }.modifier(ButtonModifier())
                        .sheet(isPresented: $synchDaily) {
                            SheetView(type: 1, connector: viewModel.connector, view: self)
                        }
                Spacer()
                Text("\(connectionLog)")
            }.alert(isPresented: $isSetDataTypes) {
                        Alert(title: Text("Set HK types to read"),
                                message: Text("All types are set"),
                                dismissButton: .default(Text("OK")))
                    }
                    .padding()
                    .navigationBarTitle(Text("Apple Health"))
        }
    }

    struct SheetView: View {
        private var type = -1
        private var connector: HKConnector
        private var view: AppleHealthView
        @State private var from = Date().dayStart
        @State private var to = Date()
        @SwiftUI.Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>

        init(type: Int, connector: HKConnector, view: AppleHealthView) {
            self.type = type
            self.connector = connector
            self.view = view
        }

        var body: some View {
            VStack(alignment: .center, spacing: 8.0) {
                Text("Synchronise \(type == 0 ? "Epoch" : "Daily")")
                if (type == 0) {
                    DatePicker("Start", selection: $from)
                            .transformEffect(.init(scaleX: 0.8, y: 0.8))
                            .datePickerStyle(WheelDatePickerStyle())
                            .padding(6.0)
                    DatePicker("End", selection: $to)
                            .datePickerStyle(WheelDatePickerStyle())
                            .padding(6.0)
                } else {
                    DatePicker("Start", selection: $from, displayedComponents: .date)
                            .transformEffect(.init(scaleX: 0.8, y: 0.8))
                            .datePickerStyle(WheelDatePickerStyle())
                            .padding(6.0)
                    DatePicker("End", selection: $to, displayedComponents: .date)
                            .transformEffect(.init(scaleX: 0.8, y: 0.8))
                            .datePickerStyle(WheelDatePickerStyle())
                            .padding(6.0)
                }
                Button("Synchronize") {
                    if (type == 0) {
                        connector.synchronizeEpoch(
                                startDate: from,
                                endDate: to,
                                types: HKConnectorType.allTypes) { (response) in
                            if response.error != nil {
                                Logger.e("SYNC HK Failed:")
                                view.connectionLog += "synchronizeEpoch Result: \(String(describing: response.error))"
                                view.localNotificationManager.scheduleNotification(
                                        Notification(
                                                title: "synchronizeEpoch:",
                                            subtitle: "Result => \(String(describing: response.error?.errorMessage))"
                                        )
                                )
                                Logger.e(String(describing: response.error))
                            }
                            Logger.i(response.successful)
                            view.connectionLog += "synchronizeEpoch Result: \(response.successful)"
                            view.localNotificationManager.scheduleNotification(
                                    Notification(
                                            title: "synchronizeEpoch:",
                                            subtitle: "Result => \(response.successful)"
                                    )
                            )
                            self.presentationMode.wrappedValue.dismiss()
                        }
                    } else {
                        connector.synchronizeDaily(
                                startDate: from,
                                endDate: to,
                                types: HKConnectorType.allTypes) { (response) in
                            if response.error != nil {
                                Logger.e("SYNC HK Failed:")
                                view.connectionLog += "synchronizeDaily Result: \(String(describing: response.error))"
                                view.localNotificationManager.scheduleNotification(
                                        Notification(
                                                title: "synchronizeDaily:",
                                            subtitle: "Result => \(String(describing: response.error?.errorMessage))"
                                        )
                                )
                                Logger.e(String(describing: response.error))
                            }
                            Logger.i(response.successful)
                            view.connectionLog += "synchronizeDaily Result: \(response.successful)"
                            view.localNotificationManager.scheduleNotification(
                                    Notification(
                                            title: "synchronizeDaily:",
                                            subtitle: "Result => \(response.successful)"
                                    )
                            )
                            self.presentationMode.wrappedValue.dismiss()
                        }
                    }
                }.modifier(ButtonModifier())
            }
        }
    }

    private func showUserData() {
        viewModel
                .coreConnector
                .getUserInformation { (userInfoArray, error) in
            if error == nil {
                var userInformation = ""
                for userInfo in userInfoArray {
                    userInformation = "Token: \(String(describing: userInfo.authenticationToken))\n"
                    userInformation += "Partner ID: \(userInfo.partnerUserID ?? "")\n"
                    userInformation += "Height: \(String(describing: userInfo.height))\n"
                    userInformation += "Weight: \(String(describing: userInfo.weight))\n"
                    userInformation += "Birthdate: \(userInfo.birthdate ?? "")\n"
                    userInformation += "Gender: \(userInfo.gender ?? "")\n"
                    if userInfo.connectedSources != nil {
                        for connectedSource in userInfo.connectedSources! {
                            userInformation += "Sources: \(connectedSource.dataSource)"
                            userInformation += " on \(connectedSource.connectedAt)\n"
                        }
                    }
                }
                Logger.d(userInformation)
            } else {
                Logger.d(error ?? "nil")
            }
        }
    }
}

struct AppleHealthView_Previews: PreviewProvider {
    static var previews: some View {
        AppleHealthView(viewModel: AppleHealthViewModel())
    }
}
