//
//  ServiceConnectorProtocol.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import Foundation
import ModuleCommons

protocol ServiceConnectorProtocol: class {
    associatedtype Connector where Connector: CommonsProtocol

    var connector: Connector { get set }
}
