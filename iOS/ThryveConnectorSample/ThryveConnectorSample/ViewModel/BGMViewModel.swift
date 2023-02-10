//
//  BGMViewModel.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import ModuleBGM
import CoreBluetooth

final class BGMViewModel: ObservableObject {
    @Published var peripherals = [CBPeripheral]()
    @Published var isBLEActivated = false
    @Published var isPaired = false
    @Published var isReady = false
    @Published var isDisconnected = false

    private var racp: CBCharacteristic?
    private var connector: BGMConnector?

    var source: BGMDevice

    init(_ source: BGMDevice) {
        self.source = source
    }

    func setConnector() {
        if connector == nil {
            connector = BGMConnector(delegate: self, source: source)
        }
    }
    func startScanning() {
        connector?.startScanning()
    }
    func stopScanning() {
        connector?.stopScanning()
    }
    func connect(device: CBPeripheral) {
        connector?.connect(peripheral: device)
    }
    func sync() {
        if racp != nil {
            connector?.sync(racp: racp!)
        }
    }
    func disconnect() {
        connector?.disconnect()
    }
    func delete(source: BGMDevice) {
        connector?.delete(source: source)
    }
    func reset() {
        peripherals = []
        isBLEActivated = false
        isPaired = false
        isReady = false
        isDisconnected = false
    }
}
// MARK: - ViewModelProtocol
extension BGMViewModel: ViewModelProtocol {
    func onAccessTokenRecieved() {
        setConnector()
    }
}
// MARK: - BLEDelegate
extension BGMViewModel: BLEDelegate {
    func ble(isActivated: Bool) {
        DispatchQueue.main.async {
            self.isBLEActivated = isActivated
        }
    }
    func ble(didFound peripheral: CBPeripheral, rssi: Int) {
        for foundDevices in peripherals where foundDevices.identifier == peripheral.identifier {
                return
        }
        DispatchQueue.main.async {
            self.peripherals.append(peripheral)
        }
    }
    func ble(didPaired peripheral: CBPeripheral, error: Error?) {
        DispatchQueue.main.async {
            self.isPaired = true
        }
    }
    func ble(ready racpCharacteristic: CBCharacteristic) {
        DispatchQueue.main.async {
            self.isReady = true
        }
        racp = racpCharacteristic
    }
    func ble(didDisconnect peripheral: CBPeripheral, error: Error?) {
        DispatchQueue.main.async {
            self.isDisconnected = true
        }
    }
}
