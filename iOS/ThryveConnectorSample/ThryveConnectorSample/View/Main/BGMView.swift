//
//  BGMView.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI
import ModuleBGM
import CoreBluetooth

struct BGMSourceListView: View {
    var body: some View {
        NavigationView {
            VStack(alignment: .center, spacing: 16.0) {
                List {
                    Section(header: Text("Supported sources: ")) {
                        NavigationLink(
                            destination: BGMView(viewModel: BGMViewModel(.bBraun)),
                            label: {
                                Text("BBraun")
                        })
                        NavigationLink(
                            destination: BGMView(viewModel: BGMViewModel(.iSens)),
                            label: {
                                Text("I-Sens")
                        })
                        NavigationLink(
                            destination: BGMView(viewModel: BGMViewModel(.rocheAccuChek)),
                            label: {
                                Text("RocheAccuchek")
                        })
                    }
                }
            }
            .padding()
            .navigationBarTitle(Text("BGM"))
        }
    }
}

struct BGMView: View, ViewProtocol {
    @ObservedObject var viewModel: BGMViewModel

    @State var isScanning: Bool = false

    var body: some View {
        VStack(alignment: .center, spacing: 16.0) {
            List(self.viewModel.peripherals, id: \.identifier) { element in
                NavigationLink(
                    destination: BraunDeviceView(device: element, viewModel: self.viewModel),
                    label: {
                        Text("\(element.name!)")
                })
            }
            Button(action: {
                self.viewModel.delete(source: self.viewModel.source)
            }, label: {
                Text("Delete")
            })
                .modifier(ButtonModifier())
        }
        .padding()
        .navigationBarTitle(Text(self.viewModel.source.string))
        .navigationBarItems(trailing: Button(action: {
            self.viewModel.startScanning()
            self.isScanning = true
        }, label: {
            Text("Start scan")
        }))
            .alert(isPresented: $isScanning, content: {
                Alert(title: Text("BLE start scanning"))
            })
            .onAppear {
                self.viewModel.setConnector()
        }
        .onDisappear {
            self.viewModel.stopScanning()
        }
    }
}

struct BraunDeviceView: View {
    var device: CBPeripheral

    @ObservedObject var viewModel: BGMViewModel

    var body: some View {
        VStack(alignment: .center, spacing: 16.0) {
            Text(
                """
                    Press Connect to device button.
                    If the deivce was not paired, you will be asked to pair it.
                    If device was already paired, the BGM device will be connected and transfer all the data.
                    After successfull data fetching, the BGM will be automatically disconnected
                """
            )
                .padding()
            if self.viewModel.isReady {
                Button(action: {
                    self.viewModel.sync()
                }, label: {
                    Text("Sync")
                })
                    .modifier(ButtonModifier())
                    .alert(isPresented: self.$viewModel.isDisconnected) {
                        Alert(
                            title: Text("Info"),
                            message: Text("Deivce is disconnected"),
                            dismissButton: .default(
                                Text("OK")
                            )
                        )
                }
            } else {
                Button(action: {
                    self.viewModel.connect(device: self.device)
                }, label: {
                    Text("Connect to device")
                })
                    .modifier(ButtonModifier())
                    .alert(isPresented: self.$viewModel.isPaired, content: {
                        Alert(
                            title: Text("Info"),
                            message: Text("Deivce is paired"),
                            dismissButton:
                            .default(
                                Text("OK"),
                                action: {
                                    self.viewModel.disconnect()
                            }))
                    })
            }
        }
        .onDisappear {
            self.viewModel.reset()
        }
    }
}
