//
//  LocalNotificationManager.swift
//  SampleCore
//
//  Created by Florian on 30.07.20.
//  Copyright © 2020 mHealthPioneers. All rights reserved.
//

import Foundation
import UserNotifications

struct Notification {
    let title: String
    let subtitle: String
}

class LocalNotificationManager {
    func requestPermission() {
        UNUserNotificationCenter.current().requestAuthorization(
            options: [
                .alert, .badge, .sound
            ]
        ) { success, error in
            if success && error == nil {
                print("All set!")
            } else {
                print(error ?? "error")
            }
        }
    }
    func scheduleNotification(_ notification: Notification) {
        let content = UNMutableNotificationContent()
        content.title = notification.title
        content.subtitle = notification.subtitle
        content.sound = UNNotificationSound.default
        let trigger = UNTimeIntervalNotificationTrigger(
            timeInterval: 1,
            repeats: false
        )
        let request = UNNotificationRequest(
            identifier: UUID().uuidString,
            content: content,
            trigger: trigger
        )
        UNUserNotificationCenter.current().add(request)
    }
}
