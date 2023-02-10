//
//  DataSource.swift
//  SampleCore
//
//  Created by Florian on 03.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct DataSource: Identifiable {
    static var allCases: [DataSource] {
        return Tracker
            .Provider
            .allCases
            .map { DataSource(tracker: Tracker($0)) }
    }

    var id: Int
    var name: String?

    private init(tracker: Tracker) {
        self.id = tracker.id
        self.name = tracker.name
    }
}
