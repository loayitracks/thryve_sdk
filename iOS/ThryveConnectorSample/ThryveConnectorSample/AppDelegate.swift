//
//  AppDelegate.swift
//  SampleCore
//
//  Created by Florian on 01.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import UIKit
import ModuleAppleHealth
import HealthKit
import CoreSDK

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        UNUserNotificationCenter.current().delegate = self
        Logger.initialize(isEnabled: true, reporting: true, verbosity: .INFO)
        HKConnector().enableBackgroundDeliveryFor(types: HKConnectorType.allTypes)
        return true
    }
}

extension AppDelegate: UNUserNotificationCenterDelegate {
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        completionHandler([.alert, .badge, .sound])
    }
}
