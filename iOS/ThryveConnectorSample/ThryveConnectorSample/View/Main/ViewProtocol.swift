//
//  ViewProtocol.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import Foundation

protocol ViewProtocol {
    associatedtype ViewModel where ViewModel: ViewModelProtocol

    var viewModel: ViewModel { get }
}
