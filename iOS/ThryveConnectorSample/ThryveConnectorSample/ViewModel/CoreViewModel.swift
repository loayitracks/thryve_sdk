//
//  CoreViewModel.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import Combine
import CoreSDK

final class CoreViewModel: ObservableObject {
    var connector: CoreConnector = CoreConnector(
        appId: Environment.appId,
        appSecret: Environment.appSecret,
        partnerUserId: nil,
        language: "de"
    )

    @Published var partnerUserId: String? {
        willSet(value) {
            if value != nil {
                connector = CoreConnector(
                    appId: Environment.appId,
                    appSecret: Environment.appSecret,
                    partnerUserId: value,
                    language: "de"
                )
            }
        }
    }
}

extension CoreViewModel: ViewModelProtocol {
    @objc func onAccessTokenRecieved() {
           NotificationCenter
            .default
            .post(name: .newAccessToken, object: nil)
       }
}
