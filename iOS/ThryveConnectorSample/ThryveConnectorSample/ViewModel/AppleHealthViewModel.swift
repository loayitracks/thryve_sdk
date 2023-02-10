//
//  AppleHealthViewModel.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import Combine
import ModuleAppleHealth
import CoreSDK
import HealthKit

final class AppleHealthViewModel: ObservableObject, ServiceConnectorProtocol {
    var connector: HKConnector = HKConnector()
    var coreConnector: CoreConnector = CoreConnector(
        appId: Environment.appId,
        appSecret: Environment.appSecret,
        partnerUserId: nil,
        language: "de"
    )

    init() {
        NotificationCenter
            .default
            .addObserver(
                self,
                selector: #selector(onAccessTokenRecieved),
                name: .newAccessToken,
                object: nil
        )
    }
}
// MARK: - ViewModelProtocol
extension AppleHealthViewModel: ViewModelProtocol {
   @objc func onAccessTokenRecieved() {
        connector = HKConnector()
    }
}
