//
//  RecognitionViewModel.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import ModuleRecognition
import CoreSDK

class RecognitionViewModel: ObservableObject, ServiceConnectorProtocol {
    var connector: RecognitionConnector = RecognitionConnector()

    @Published var isStepsOn: Bool {
        willSet(value) {
            runStepDetection(isOn: value)
        }
    }
    @Published var isActivityOn: Bool {
        willSet(value) {
            runActivityRecognition(isOn: value)
        }
    }
    @Published var isBestAccuracy: Bool
    @Published var isLocationOn: Bool {
        willSet(value) {
            runLocationTracking(isOn: value)
        }
    }
    @Published var isWellbeingOn: Bool {
        willSet(value) {
            runWellbeingDetection(isOn: value)
        }
    }

    init() {
        isStepsOn = connector.isEnabled(.step)
        isActivityOn = connector.isEnabled(.activity)
        isBestAccuracy = connector.isLocationBestAccuracy()
        isLocationOn = connector.isEnabled(.location)
        isWellbeingOn = connector.isEnabled(.analytics)
        runStepDetection(isOn: isStepsOn)
        runActivityRecognition(isOn: isActivityOn)
        runLocationTracking(isOn: isBestAccuracy)
        runWellbeingDetection(isOn: isWellbeingOn)
        NotificationCenter
            .default
            .addObserver(
                self,
                selector: #selector(onAccessTokenRecieved),
                name: .newAccessToken,
                object: nil
        )
    }

    private func runStepDetection(isOn: Bool) {
        if isOn {
            connector.startStepDetection { (_, error) in
                Logger.e(error)
            }
        } else {
            connector.stopStepDetection { (_, error) in
                Logger.e(error)
            }
        }
    }
    private func runActivityRecognition(isOn: Bool) {
        if isOn {
            connector.startActivityRecognition { (_, error) in
                Logger.e(error)
            }
        } else {
            connector.stopActivityRecognition { (_, error) in
                Logger.e(error)
            }
        }
    }
    private func runLocationTracking(isOn: Bool) {
        if isOn {
            connector.startLocationTracking(bestAccuracy: isBestAccuracy) { (_, error) in
                Logger.e(error)
            }
        } else {
            connector.stopLocationTracking { (_, error) in
                Logger.e(error)
            }
        }
    }
    private func runWellbeingDetection(isOn: Bool) {
        if isOn {
            connector.startWellbeingIndicatorsDetection { (_, error) in
                Logger.e(error)
            }
        } else {
            connector.stopWellbeingIndicatorsDetection { (_, error) in
                Logger.e(error)
            }
        }
    }
}
// MARK: - ViewModelProtocol
extension RecognitionViewModel: ViewModelProtocol {
    @objc func onAccessTokenRecieved() {
         connector = RecognitionConnector()
     }
}
