//
//  Coordinator.swift
//  SampleCore
//
//  Created by Florian on 17.05.20.
//  Copyright © 2020 mHealthPioneers. All rights reserved.
//

import WebKit
import SwiftUI
import CoreSDK

class Coordinator: NSObject, CoreWebViewDelegate {

    var connector: CoreConnector?

    let connection: Bool

    let instantRevoke: Bool

    var source: Tracker?

    init(connector: CoreConnector?, connection: Bool, instantRevoke: Bool, source: Tracker?) {
        self.connector = connector
        self.connection = connection
        self.instantRevoke = instantRevoke
        self.source = source
        super.init()
    }

    func webView(_ webView: WKWebView, source: Tracker?, connected: Bool, error: Error?) {
        print(source?.id ?? 0)
        print(connected)
        print(error ?? 0)
    }

}
