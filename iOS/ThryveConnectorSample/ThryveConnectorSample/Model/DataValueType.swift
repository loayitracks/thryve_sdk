//
//  DataValueType.swift
//  SampleCore
//
//  Created by Florian on 03.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct DataValueType: Identifiable {
    static var allCases: [DataValueType] {
        return ValueType
            .allCases
            .map { DataValueType(id: Int($0.rawValue), type: $0) }
    }

    var id: Int
    var type: ValueType
}
