//
//  WebViewModel.swift
//  SampleCore
//
//  Created by Florian on 03.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import Foundation
import CoreSDK

final class WebViewModel {
    let connector: CoreConnector

    init(connector: CoreConnector) {
        self.connector = connector
    }
}
