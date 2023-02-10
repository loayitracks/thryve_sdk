//
//  CoreView.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK
import UserNotifications

struct CoreView: View, ViewProtocol {
    private let localNotificationManager = LocalNotificationManager()

    var version: String? {
        return Bundle
            .main
            .infoDictionary?["CFBundleShortVersionString"] as? String
    }

    @ObservedObject var viewModel: CoreViewModel

    @State private var isUserInfoAlertPresented = false
    @State private var userInformation = ""
    @State private var partnerUserId = "HelloApple"
    @State private var isCustomerUpload = false

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 16.0) {
                    Image("mainLogo")
                    Text("version: \(version ?? "unknown")")
                    VStack(alignment: .center, spacing: 16.0) {
                        TextField("Enter partner user id", text: $partnerUserId)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .frame(width: 300, height: 50, alignment: .center)
                        Button(action: {
                            self.viewModel.partnerUserId = self.partnerUserId
                            self.viewModel
                                .connector
                                .getAccessToken(completionHandler: { (accessToken, error) in
                                    if accessToken != nil {
                                        self.localNotificationManager.scheduleNotification(
                                            Notification(
                                                title: "Token for \(self.partnerUserId)",
                                                subtitle: accessToken!
                                            )
                                        )
                                        self.viewModel.onAccessTokenRecieved()
                                    } else {
                                        Logger.e(error)
                                    }
                                })

                        }, label: {
                            Text("Get accessToken")
                        })
                        .modifier(ButtonModifier())
                        Button(action: {
                            self.viewModel.connector.setAccessToken("dfnsdfskfdskfjdkjfe445refd")
                        }, label: {
                            Text("Set accessToken")
                        })
                        .modifier(ButtonModifier())
                        NavigationLink(
                            destination: DirectConnectionWebViewController(
                                connector: self.viewModel.connector,
                                connection: false,
                                instantRevoke: false,
                                source: nil
                            )
                        ) {
                            Text("Get data source list")
                        }
                        .modifier(ButtonModifier())
                        NavigationLink(
                            destination: DirectConnectionView(
                                connector: self.viewModel.connector
                            )
                        ) {
                            Text("Direct connection/revoke")
                        }
                        .modifier(ButtonModifier())
                        Button(action: {
                            self.viewModel
                                .connector
                                .getUserInformation(completionHandler: { (userInfoArray, error) in
                                    if error == nil {
                                        var userInformation = ""
                                        for userInfo in userInfoArray {
                                            userInformation = "Token: \(userInfo.authenticationToken)\n"
                                            userInformation += "Partner ID: \(userInfo.partnerUserID ?? "")\n"
                                            userInformation += "Height: \(userInfo.height)\n"
                                            userInformation += "Weight: \(userInfo.weight)\n"
                                            userInformation += "Birthdate: \(userInfo.birthdate ?? "")\n"
                                            userInformation += "Gender: \(userInfo.gender ?? "")\n"
                                            if userInfo.connectedSources != nil {
                                                for connectedSource in userInfo.connectedSources! {
                                                    userInformation += "Sources: \(connectedSource.dataSource)"
                                                    userInformation += " on \(connectedSource.connectedAt)\n"
                                                }
                                            }
                                        }
                                        DispatchQueue.main.async {
                                            self.userInformation = userInformation
                                            self.isUserInfoAlertPresented = true
                                        }
                                    } else {
                                        print(error)
                                    }
                                })
                        }, label: {
                            Text("Get user info")
                        })
                        .modifier(ButtonModifier())
                        .alert(isPresented: $isUserInfoAlertPresented) {
                            Alert(
                                title: Text("User information"),
                                message: Text(self.userInformation),
                                dismissButton: Alert.Button.cancel()
                            )
                        }
                        NavigationLink(
                            destination: CustomerBackchannelView(connector: self.viewModel.connector),
                            label: {
                                Text("Upload customer value")
                            })
                            .modifier(ButtonModifier())
                        NavigationLink(
                            destination: UserBackChannelView(connector: self.viewModel.connector),
                            label: {
                                Text("Upload user information")
                            })
                            .modifier(ButtonModifier())
                    }
                    .padding()
                    .navigationBarTitle(Text("Core"))
                }
                .onAppear {
                    self.localNotificationManager.requestPermission()
                }
            }
        }
    }
}

func randomString(length: Int) -> String {
  let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  return String((0..<length).map{ _ in letters.randomElement()! })
}

struct CoreView_Previews: PreviewProvider {
    static var previews: some View {
        CoreView(viewModel: CoreViewModel())
    }
}
