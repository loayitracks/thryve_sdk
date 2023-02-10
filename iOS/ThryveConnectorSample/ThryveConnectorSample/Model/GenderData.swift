//
//  GenderData.swift
//  SampleCore
//
//  Created by Florian on 11.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import CoreSDK

struct GenderData: Identifiable {
    static var allCases: [GenderData] {
        return Gender
            .allCases
            .map { GenderData(gender: $0) }
    }

    var id: Int
    var name: String

    private init(gender: Gender) {
        self.id = gender.rawValue
        self.name = gender.string
    }
}
