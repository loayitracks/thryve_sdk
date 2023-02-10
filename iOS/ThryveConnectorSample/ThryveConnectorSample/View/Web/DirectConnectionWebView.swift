//
//  DirectConnectionWebView.swift
//  SampleCore
//
//  Created by Florian on 03.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import WebKit
import SwiftUI
import CoreSDK

struct DirectConnectionWebViewController: UIViewControllerRepresentable {
    var connector: CoreConnector?
    let connection: Bool
    let instantRevoke: Bool
    var source: Tracker?

    func makeUIViewController(context: Context) -> ViewController {
        return ViewController.make(
            connector: connector,
            connection: connection,
            instantRevoke: instantRevoke,
            source: source
        )
    }
    func updateUIViewController(_ uiViewController: ViewController, context: Context) {}
}

class ViewController: CoreWebViewController, CoreWebViewDelegate {
    var connector: CoreConnector?
    var connection: Bool = true
    var instantRevoke: Bool = false
    var source: Tracker?

    override func viewDidLoad() {
        super.viewDidLoad()
        coreWebViewDelegate = self
        if source == nil {
            loadDataSourceConnection()
        } else {
            connection ? loadDirectConnection() : loadDirectRevoke()
        }
    }

    func webView(
        _ webView: WKWebView,
        source: Tracker?,
        connected: Bool,
        error: Error?
    ) {
        Logger.i(source?.id)
        Logger.i(connected)
        Logger.e(error)
    }
    
    func webView(_ webView: WKWebView, source: Tracker?, connected: Bool, hasSocialLoginError: Bool, error: Error?) {
        Logger.i(source?.id)
        Logger.i(connected)
        Logger.i(hasSocialLoginError)
        Logger.e(error)
    }
}

extension ViewController {
    static func make(
        connector: CoreConnector?,
        connection: Bool,
        instantRevoke: Bool,
        source: Tracker?
    ) -> ViewController {
        let viewController = ViewController()
        viewController.connector = connector
        viewController.connection = connection
        viewController.instantRevoke = instantRevoke
        viewController.source = source
        return viewController
    }
}

struct DirectConnectionWebView_Previews: PreviewProvider {
    static var previews: some View {
        /*@START_MENU_TOKEN@*/Text("Hello, World!")/*@END_MENU_TOKEN@*/
    }
}
