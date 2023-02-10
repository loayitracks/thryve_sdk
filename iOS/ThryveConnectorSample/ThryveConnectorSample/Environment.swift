//
//  Environment.swift
//  SampleCore
//
//  Created by Florian on 24.05.20.
//  Copyright © 2020 mHealthPioneers. All rights reserved.
//

import Foundation

final class Environment {
    enum Keys: String {
        case appId = "APP_ID"
        case appSecret = "APP_SECRET"
    }

    static let appId: String = {
        guard let appId = Environment
            .infoDictionary[Keys.appId.rawValue] as? String
            else {
            fatalError("APP ID not set in plist for this environment")
        }
        return appId
    }()
    static let appSecret: String = {
        guard let appSecret = Environment
            .infoDictionary[Keys.appSecret.rawValue] as? String
            else {
            fatalError("APP Secret not set in plist for this environment")
        }
        return appSecret
    }()
    private static let infoDictionary: [String: Any] = {
        guard let dict = Bundle.main.infoDictionary else {
            fatalError("Plist file not found")
        }
        return dict
    }()
}
