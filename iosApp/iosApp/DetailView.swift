//
//  DetailView.swift
//  iosApp
//
//  Created by Khaled Alrusan on 2025-07-22.
//

import Foundation
import SwiftUI
import Shared
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI

struct DetailView: View {
    let objectId: Int32

    @ObservedViewModel
    var viewModel: DetailViewModel

    init(objectId: Int32) {
        self.objectId = objectId
        _viewModel = ObservedViewModel(wrappedValue: DetailViewModel(
            museumRepository: KoinDependencies().museumRepository
        ))
    }

    var body: some View {
        Group {
            switch viewModel.state {
            case is DetailViewState.Loading:
                ProgressView()
            case let error as DetailViewState.Error:
                Text(error.message)
            case let content as DetailViewState.Content:
                if let obj = content.museumObject {
                    ObjectDetails(obj: obj)
                } else {
                    Text(SharedStrings().noDataAvailable)
                }
            default:
                Text("Unknown state")
            }
        }.onAppear{
            viewModel.dispatch(intent: DetailIntent.LoadMuseum(objectId: objectId))
        }
    }
}

struct ObjectDetails: View {
    var obj: MuseumObject

    var body: some View {
        ScrollView {

            VStack {
                AsyncImage(url: URL(string: obj.primaryImageSmall)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .clipped()
                    default:
                        EmptyView()
                    }
                }

                VStack(alignment: .leading, spacing: 6) {
                    Text(obj.title)
                        .font(.title)

                    LabeledInfo(label: SharedStrings().labelArtist, data: obj.artistDisplayName)
                    LabeledInfo(label: SharedStrings().labelDate, data: obj.objectDate)
                    LabeledInfo(label: SharedStrings().labelDimensions, data: obj.dimensions)
                    LabeledInfo(label: SharedStrings().labelMedium, data: obj.medium)
                    LabeledInfo(label: SharedStrings().labelDepartment, data: obj.department)
                    LabeledInfo(label: SharedStrings().labelRepository, data: obj.repository)
                    LabeledInfo(label: SharedStrings().labelCredits, data: obj.creditLine)
                }
                .padding(16)
            }
        }
    }
}

struct LabeledInfo: View {
    var label: String
    var data: String

    var body: some View {
        Spacer()
        Text("**\(label):** \(data)")
    }
}
